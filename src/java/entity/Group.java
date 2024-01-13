/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author leduy
 */
public class Group extends BaseEntity {
    private ArrayList<Session> sessions = new ArrayList<>();
    private String group_id;
    private String group_name;
    private String link_url;

    public Group() {
    }

    public Group(String class_id, String class_name, String link_url) {
        this.group_id = class_id;
        this.group_name = class_name;
        this.link_url = link_url;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
    
}
