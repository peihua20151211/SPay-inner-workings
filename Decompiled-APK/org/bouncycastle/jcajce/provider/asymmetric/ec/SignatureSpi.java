package org.bouncycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.NullDigest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.ECNRSigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.jcajce.provider.asymmetric.util.DSABase;
import org.bouncycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;

public class SignatureSpi extends DSABase {

    private static class PlainDSAEncoder implements DSAEncoder {
        private PlainDSAEncoder() {
        }

        private byte[] makeUnsigned(BigInteger bigInteger) {
            Object toByteArray = bigInteger.toByteArray();
            if (toByteArray[0] != null) {
                return toByteArray;
            }
            Object obj = new byte[(toByteArray.length - 1)];
            System.arraycopy(toByteArray, 1, obj, 0, obj.length);
            return obj;
        }

        public BigInteger[] decode(byte[] bArr) {
            BigInteger[] bigIntegerArr = new BigInteger[2];
            Object obj = new byte[(bArr.length / 2)];
            Object obj2 = new byte[(bArr.length / 2)];
            System.arraycopy(bArr, 0, obj, 0, obj.length);
            System.arraycopy(bArr, obj.length, obj2, 0, obj2.length);
            bigIntegerArr[0] = new BigInteger(1, obj);
            bigIntegerArr[1] = new BigInteger(1, obj2);
            return bigIntegerArr;
        }

        public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2) {
            Object makeUnsigned = makeUnsigned(bigInteger);
            Object makeUnsigned2 = makeUnsigned(bigInteger2);
            Object obj = makeUnsigned.length > makeUnsigned2.length ? new byte[(makeUnsigned.length * 2)] : new byte[(makeUnsigned2.length * 2)];
            System.arraycopy(makeUnsigned, 0, obj, (obj.length / 2) - makeUnsigned.length, makeUnsigned.length);
            System.arraycopy(makeUnsigned2, 0, obj, obj.length - makeUnsigned2.length, makeUnsigned2.length);
            return obj;
        }
    }

    private static class StdDSAEncoder implements DSAEncoder {
        private StdDSAEncoder() {
        }

        public BigInteger[] decode(byte[] bArr) {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(bArr);
            return new BigInteger[]{ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue(), ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue()};
        }

        public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(new ASN1Integer(bigInteger));
            aSN1EncodableVector.add(new ASN1Integer(bigInteger2));
            return new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER);
        }
    }

    public static class ecCVCDSA224 extends SignatureSpi {
        public ecCVCDSA224() {
            super(new SHA224Digest(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA256 extends SignatureSpi {
        public ecCVCDSA256() {
            super(new SHA256Digest(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA384 extends SignatureSpi {
        public ecCVCDSA384() {
            super(new SHA384Digest(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA512 extends SignatureSpi {
        public ecCVCDSA512() {
            super(new SHA512Digest(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecCVCDSA extends SignatureSpi {
        public ecCVCDSA() {
            super(new SHA1Digest(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    public static class ecDSA224 extends SignatureSpi {
        public ecDSA224() {
            super(new SHA224Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA256 extends SignatureSpi {
        public ecDSA256() {
            super(new SHA256Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA384 extends SignatureSpi {
        public ecDSA384() {
            super(new SHA384Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA512 extends SignatureSpi {
        public ecDSA512() {
            super(new SHA512Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSA extends SignatureSpi {
        public ecDSA() {
            super(new SHA1Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSARipeMD160 extends SignatureSpi {
        public ecDSARipeMD160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDSAnone extends SignatureSpi {
        public ecDSAnone() {
            super(new NullDigest(), new ECDSASigner(), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA224 extends SignatureSpi {
        public ecDetDSA224() {
            super(new SHA224Digest(), new ECDSASigner(new HMacDSAKCalculator(new SHA224Digest())), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA256 extends SignatureSpi {
        public ecDetDSA256() {
            super(new SHA256Digest(), new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest())), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA384 extends SignatureSpi {
        public ecDetDSA384() {
            super(new SHA384Digest(), new ECDSASigner(new HMacDSAKCalculator(new SHA384Digest())), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA512 extends SignatureSpi {
        public ecDetDSA512() {
            super(new SHA512Digest(), new ECDSASigner(new HMacDSAKCalculator(new SHA512Digest())), new StdDSAEncoder());
        }
    }

    public static class ecDetDSA extends SignatureSpi {
        public ecDetDSA() {
            super(new SHA1Digest(), new ECDSASigner(new HMacDSAKCalculator(new SHA1Digest())), new StdDSAEncoder());
        }
    }

    public static class ecNR224 extends SignatureSpi {
        public ecNR224() {
            super(new SHA224Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR256 extends SignatureSpi {
        public ecNR256() {
            super(new SHA256Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR384 extends SignatureSpi {
        public ecNR384() {
            super(new SHA384Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR512 extends SignatureSpi {
        public ecNR512() {
            super(new SHA512Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecNR extends SignatureSpi {
        public ecNR() {
            super(new SHA1Digest(), new ECNRSigner(), new StdDSAEncoder());
        }
    }

    public static class ecPlainDSARP160 extends SignatureSpi {
        public ecPlainDSARP160() {
            super(new RIPEMD160Digest(), new ECDSASigner(), new PlainDSAEncoder());
        }
    }

    SignatureSpi(Digest digest, DSA dsa, DSAEncoder dSAEncoder) {
        super(digest, dsa, dSAEncoder);
    }

    protected void engineInitSign(PrivateKey privateKey) {
        CipherParameters generatePrivateKeyParameter = ECUtil.generatePrivateKeyParameter(privateKey);
        this.digest.reset();
        if (this.appRandom != null) {
            this.signer.init(true, new ParametersWithRandom(generatePrivateKeyParameter, this.appRandom));
        } else {
            this.signer.init(true, generatePrivateKeyParameter);
        }
    }

    protected void engineInitVerify(PublicKey publicKey) {
        CipherParameters generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(publicKey);
        this.digest.reset();
        this.signer.init(false, generatePublicKeyParameter);
    }
}
