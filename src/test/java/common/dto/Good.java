package common.dto;

public class Good {
    private String NDS;
    private String name;
    private String price;
    private String address;
    private String producer;
    private String producerCountry;
    private String weight;
    private String brand;
    private String expirationDate;
    private String temperatureReglament;
    private String path;
    private String category;


    public Good() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducerCountry() {
        return producerCountry;
    }

    public void setProducerCountry(String producerCountry) {
        this.producerCountry = producerCountry;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setNDS(String NDS) {
        this.NDS = NDS;
    }


    public String toString() {
        return "name: " + name + " price: " + price + " address: " + address + " producer: " + producer + " producerCountry: " + producerCountry;
    }


    public String getNDS() {
        return NDS;
    }

    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setTemperatureReglament(String value) {
        this.temperatureReglament = value;
    }

    public String getTemperatureReglament() {
        return temperatureReglament;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
