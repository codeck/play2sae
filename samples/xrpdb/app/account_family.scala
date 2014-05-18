package org.codeck.sbtripple

import java.math.BigInteger;

object AccountFamily {
  import BigInteger.{ZERO, ONE}
  import org.bouncycastle.math.ec.{ECPoint, ECCurve}
  import org.bouncycastle.util.encoders.Hex
  import org.bouncycastle.asn1.sec.SECNamedCurves
  import org.bouncycastle.crypto.digests.{SHA256Digest, SHA512Digest, RIPEMD160Digest}
  val ecparams = SECNamedCurves.getByName("secp256k1")
  val paramN = ecparams.getN()

  val ripple58Dict = "rpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcdeCg65jkm8oFqi1tuvAxyz"

  private[this] def SHA512Half(input :Array[Byte]) = {
	val dgst = new SHA512Digest
	val result512 = new Array[Byte](64)
	dgst.update(input, 0, input.length)
	dgst.doFinal(result512, 0)
	unsignedBN(result512.take(32))
  }

  def unsignedBN(input: Array[Byte]) = new BigInteger(1, input)
	
  private[this] def padBN(bn :BigInteger, len :Int) = {
	bn.toByteArray().toList.reverse.padTo(len, 0:Byte).reverse.take(len)
  }

  private[this] def padCons(first :Seq[Byte], conseq :Int) = {
	first ++ BigInteger.valueOf(conseq).toByteArray.toList.reverse.padTo(4, 0:Byte).reverse
  }

  private[this] def Base58en(bn: BigInteger) = {
	val base = BigInteger.valueOf(ripple58Dict.length)
	var nbn = bn
	val iter = Iterator.iterate(bn)(_.divide(base)).takeWhile(_.compareTo(ZERO) > 0).map(x => ripple58Dict.charAt(x.mod(base).intValue))	
	iter.toList.reverse.mkString
  }

  private[this] def HumanEncode(payload :Seq[Byte], version :Byte) = {
	val dgst = new SHA256Digest
	val result256 = new Array[Byte](32)
	(version +: payload).foreach(dgst.update)
	dgst.doFinal(result256, 0)
	dgst.reset
	dgst.update(result256, 0, result256.length)
	dgst.doFinal(result256, 0)
	val checksum = result256.take(4)
	val bytes = (version +: payload) ++ checksum
	if (version == 0) {
	  Base58en(unsignedBN(bytes toArray)).reverse.padTo(26, ripple58Dict(0)).reverse
	}
	else Base58en(unsignedBN(bytes toArray))
  }

  def TestVectors() {
	{
	  val acctZero = "rrrrrrrrrrrrrrrrrrrrrhoLvTp"
	  assert('r'+:HumanEncode(padBN(BigInteger.valueOf(0), 20), 0) == acctZero)
	}
	{
	  val rightret = unsignedBN(Hex.decode("B8244D028981D693AF7B456AF8EFA4CAD63D282E19FF14942C246E50D9351D22"))
	  assert(SHA512Half(Array[Byte](0)).compareTo(rightret) == 0)
	}
	{
	  val rightret = unsignedBN(Hex.decode("8EEE2EA9E7F93AB0D9E66EE4CE696D6824922167784EC7F340B3567377B1CE64"))
	  val arr:Array[Byte] = padCons(Seq.empty, 100000).toArray
	  assert(SHA512Half(arr).compareTo(rightret) == 0)
	}
	{
	  val seed = unsignedBN(Hex.decode("71ED064155FFADFA38782C5E0158CB26"))
	  val root = RootKey(seed)

	  val humanSeed = "shHM53KPZ87Gwdqarm1bAmPeXg8Tn"
	  assert(root.toString('s') == humanSeed)

	  val privGen = unsignedBN(Hex.decode("7CFBA64F771E93E817E15039215430B53F7401C34931D111EAB3510B22DBB0D8"))
	  assert(root.privGen.compareTo(privGen) == 0)

	  val pubGen = "fht5yrLWh3P8DrJgQuVNDPQVXGTMyPpgRHFKGQzFQ66o3ssesk3o"
	  assert(root.toString('f') == pubGen)

	  val pubKey = "aBRoQibi2jpDofohooFuzZi9nEzKw9Zdfc4ExVNmuXHaJpSPh8uJ"
	  assert(root.toString('a') == pubKey)

	  val acct = "rhcfR9Cg98qCxHpCcPBmMonbDBXo84wyTn"
	  assert(root.toString('r') == acct)
	}
  }

  def test() {
	TestVectors()
	//println(() map(c => "%02x".format(c)) mkString(" ")))
	var seed = unsignedBN(Hex.decode("71ED064155FFADFA38782C5E0158CB26"))
	val maxround = 1000
	var round = maxround
	val startt = System.currentTimeMillis();
	while (round > 0
		 ) {
	  val root = new RootKey(seed)
	  //(root.toString('r'), root.toString('s'))
	  (root.privGen, root.pubGenPoint)
	  round = round-1
	  seed = seed.add(ONE)
	}
	val endt = System.currentTimeMillis();
	println(s"${(endt-startt)/1000} secs elasped, average ${((endt-startt):Double)/maxround} msecs per round")
  }

  case class RootKey(seed:BigInteger, initseq:Int = -1, initsubseq:Int = -1) {
	private[this] val dgst = new SHA512Digest
	private[this] val result512 = new Array[Byte](64)
	private[this] var seq = initseq
	def seqNum = seq
	private[this] var subseq = initsubseq
	def subSeqNum = subseq

	private[this] def compressPoint(pt:ECPoint) = {
	  val cpub = new ECPoint.Fp(ecparams.getCurve, pt.getX, pt.getY, true)
	  cpub.getEncoded
	}

	def toString(version:Char):String = {
	  version match {
		// case 'n' => //validation_public_key 
		//   "28"
		// case 'p' => //validation_private_key
		//   "32"
		case 'r' => //account_id 
		  {
			val hash256 = new Array[Byte](32)
			val hash160 = new Array[Byte](20)
			val input = pubKey
			val sha = new SHA256Digest
			val rmd = new RIPEMD160Digest
			sha.update(input, 0, input.length)
			sha.doFinal(hash256, 0)
			rmd.update(hash256, 0, hash256.length)
			rmd.doFinal(hash160, 0)
			'r' +: HumanEncode(hash160, 0)
		  }
		case 'a' => //account_public_key 
		  HumanEncode(pubKey, 35)
		case 'p' => //account_private_key
		  HumanEncode(padBN(privKey, 32), 34)
		case 'f' => //family_public_generator
		  HumanEncode(pubGen, 41)	
		case 's' => //family_seed | validation_seed
		  HumanEncode(padBN(seed, 16), 33)
		case _ =>
		  "??"
	  }
	}

	lazy val pubGenPoint = ecparams.getG().multiply(privGen)
	lazy val pubGen = compressPoint(pubGenPoint)
	lazy val privGen = {
	  var priv256 = seed
	  do {
		seq = seq + 1
		val input160 = padCons(padBN(seed, 16), seq).toArray
		priv256 = SHA512Half(input160)
	  }
	  while (priv256.equals(ZERO)  || (priv256.compareTo(paramN) >= 0))
	  priv256
	}

	lazy val pubKeyPoint = pubGenPoint.add(ecparams.getG().multiply(privHash))
	lazy val pubKey = compressPoint(pubKeyPoint)
	lazy val privHash = {
	  var priv256 = seed
	  do {
	   subseq = subseq + 1
		val input = padCons(padCons(pubGen, seq), subseq).toArray
		priv256 = SHA512Half(input)
	  }
	  while (priv256.equals(ZERO)  || (priv256.compareTo(paramN) >= 0))
	  priv256	  
	}
	lazy val privKey = privGen.add(privHash).mod(paramN)
  }

  def GenerateRootDeterministicKey(seed: BigInteger) = {
	val root = RootKey(seed)
	root.privGen
  }
  def GeneratePublicDeterministicKey(seed: BigInteger) = {
	val root = RootKey(seed)
	root.pubKey
  }
  def GeneratePrivateDeterministicKey(seed: BigInteger) {
	val root = RootKey(seed)
	root.privKey
  }
}

