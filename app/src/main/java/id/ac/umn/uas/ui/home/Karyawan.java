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
}
