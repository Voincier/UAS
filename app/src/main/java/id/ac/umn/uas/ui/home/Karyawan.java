package id.ac.umn.uas.ui.home;

public class Karyawan {
    public String mEmail,mName,mPassword,mJob,mMatkul,mBio,mGaji,mKelas,mInfo,mFoto,mId;

    public Karyawan(){}

    public Karyawan(String mJob,String mName,String mFoto,String mId){
        this.mId = mId;
        this.mJob = mJob;
        this.mName = mName;
        this.mFoto = mFoto;
    }

    public Karyawan(String mJob,String mName,String mFoto,String mId,String mMatkul,String mBio, String mKelas, String mInfo){
        this.mId = mId;
        this.mJob = mJob;
        this.mName = mName;
        this.mFoto = mFoto;
        this.mMatkul = mMatkul;
        this.mBio = mBio;
        this.mKelas = mKelas;
        this.mInfo = mInfo;
    }

    public String getId(){
        return this.mId;
    }

    public void setId(String mId){
        this.mId = mId;
    }

    public String getName(){
        return this.mName;
    }

    public void setName(String mName){
        this.mName = mName;
    }

    public String getJob(){
        return this.mJob;
    }

    public void setJob(String mJob){
        this.mJob = mJob;
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

    public String getKelas() { return this.mKelas; }

    public void setKelas(String mKelas) { this.mKelas = mKelas; }

    public String getInfo() { return this.mInfo; }

    public void setInfo(String mInfo) { this.mInfo = mInfo; }
}
