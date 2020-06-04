package id.ac.umn.uas.ui.home;

public class Karyawan {
    public String mEmail,mNama,mPassword,mJabatan,mMatkul,mBio,mGaji,mKodeKelas,mInfo,mFoto,mId;

    public Karyawan(){}

    public Karyawan(String mJabatan,String mNama,String mFoto,String mId){
        this.mId = mId;
        this.mJabatan = mJabatan;
        this.mNama = mNama;
        this.mFoto = mFoto;
    }

    public Karyawan(String mJabatan,String mNama,String mFoto,String mId,String mMatkul,String mBio, String mKodeKelas, String mInfo){
        this.mId = mId;
        this.mJabatan = mJabatan;
        this.mNama = mNama;
        this.mFoto = mFoto;
        this.mMatkul = mMatkul;
        this.mBio = mBio;
        this.mKodeKelas = mKodeKelas;
        this.mInfo = mInfo;
    }

    public String getId(){
        return this.mId;
    }

    public void setId(String mId){
        this.mId = mId;
    }

    public String getNama(){
        return this.mNama;
    }

    public void setNama(String mNama){
        this.mNama = mNama;
    }

    public String getJabatan(){
        return this.mJabatan;
    }

    public void setjabatan(String mJabatan){
        this.mJabatan = mJabatan;
    }

    public String getFoto(){
        return this.mFoto;
    }

    public void setFoto(String mFoto){
        this.mFoto = mFoto;
    }

    public String getMatkul() { return this.mMatkul; }

    public void setMatkul(String mMatkul) { this.mMatkul = mMatkul; }

    public String getBio() { return this.mBio; }

    public void getBio(String mBio) { this.mBio = mBio; }

    public String getEmail() { return this.mEmail; }

    public void setEmail(String mEmail) { this.mEmail = mEmail; }

    public String getKodeKelas() { return this.mKodeKelas; }

    public void setKodeKelas(String mKodeKelas) { this.mKodeKelas = mKodeKelas; }

    public String getInfo() { return this.mInfo; }

    public void setInfo(String mInfo) { this.mInfo = mInfo; }
}
