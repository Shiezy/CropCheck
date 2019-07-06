package model;

import com.google.gson.annotations.SerializedName;

public class Site {

    @SerializedName("created_at")
    private String created_at;
    @SerializedName("physical_add")
    private String physical_add;

    public Site(String created_at, String physical_add){
        this.created_at = created_at;
        this.physical_add = physical_add;
    }

    public String getCreated_at(){
        return created_at;
    }

    public String getPhysical_add(){
        return physical_add;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setPhysical_add(String physical_add) {
        this.physical_add = physical_add;
    }
}
