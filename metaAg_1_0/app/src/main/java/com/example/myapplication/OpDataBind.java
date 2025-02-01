package com.example.myapplication;

public class OpDataBind {
        String Operator, Entry,Exit,Duration,Activity,Crop, Croprate, Activity_type,Asset_used, Asset_description, Asset_note, Implement, Material, Material_type, Material_amount, Work_amount;

    OpDataBind(){

    }

    public OpDataBind(String operator, String entry, String exit, String duration, String activity, String crop, String croprate, String activity_type, String asset_used, String asset_description, String asset_note, String implement, String material, String material_type, String material_amount, String work_amount) {
        Operator = operator;
        Entry = entry;
        Exit = exit;
        Duration = duration;
        Activity = activity;
        Crop = crop;
        Croprate = croprate;
        Activity_type = activity_type;
        Asset_used = asset_used;
        Asset_description = asset_description;
        Asset_note = asset_note;
        Implement = implement;
        Material = material;
        Material_type = material_type;
        Material_amount = material_amount;
        Work_amount = work_amount;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getEntry() {
        return Entry;
    }

    public void setEntry(String entry) {
        Entry = entry;
    }

    public String getExit() {
        return Exit;
    }

    public void setExit(String exit) {
        Exit = exit;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getCrop() {
        return Crop;
    }

    public void setCrop(String crop) {
        Crop = crop;
    }

    public String getCroprate() {
        return Croprate;
    }

    public void setCroprate(String croprate) {
        Croprate = croprate;
    }

    public String getActivity_type() {
        return Activity_type;
    }

    public void setActivity_type(String activity_type) {
        Activity_type = activity_type;
    }

    public String getAsset_used() {
        return Asset_used;
    }

    public void setAsset_used(String asset_used) {
        Asset_used = asset_used;
    }

    public String getAsset_description() {
        return Asset_description;
    }

    public void setAsset_description(String asset_description) {
        Asset_description = asset_description;
    }

    public String getAsset_note() {
        return Asset_note;
    }

    public void setAsset_note(String asset_note) {
        Asset_note = asset_note;
    }

    public String getImplement() {
        return Implement;
    }

    public void setImplement(String implement) {
        Implement = implement;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getMaterial_type() {
        return Material_type;
    }

    public void setMaterial_type(String material_type) {
        Material_type = material_type;
    }

    public String getMaterial_amount() {
        return Material_amount;
    }

    public void setMaterial_amount(String material_amount) {
        Material_amount = material_amount;
    }

    public String getWork_amount() {
        return Work_amount;
    }

    public void setWork_amount(String work_amount) {
        Work_amount = work_amount;
    }
}
