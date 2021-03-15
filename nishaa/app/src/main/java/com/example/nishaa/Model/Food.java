package com.example.nishaa.Model;

public class Food {
    private String name, image, description, price, discount, menuId;

    public Food(){

    }

    public Food(String Name, String Image, String Description, String Price, String Discount, String MenuId) {
        name = Name;
        image = Image;
       description = Description;
        price = Price;
        discount = Discount;
       menuId = MenuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String Image) {
        image = Image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        description = Description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String Price) {
        price = Price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String Discount) {
        discount = Discount;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String MenuId) {
        menuId = MenuId;
    }
}
