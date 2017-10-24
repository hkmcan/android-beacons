package com.example.user.uyari;

public class PlacemarkCustom {
    String name;
    int width, height;
    double x,y;

    public PlacemarkCustom(String name, int width, int height, double x, double y) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlacemarkCustom placemarkCustom = (PlacemarkCustom) o;

        if (width != placemarkCustom.width) return false;
        if (height != placemarkCustom.height) return false;
        if (Double.compare(placemarkCustom.x, x) != 0) return false;
        if (Double.compare(placemarkCustom.y, y) != 0) return false;
        return name != null ? name.equals(placemarkCustom.name) : placemarkCustom.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + width;
        result = 31 * result + height;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
