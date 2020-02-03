package model.entity;

public class Bonus extends Occupant {
    private transient String item;
    private static final long serialVersionUID = 1L;

    public Bonus(String itemName) {
        this.item = itemName;
    }

    public String getItem() {
        return item;
    }

    /**
     * Returns the path to the item in a string format.
     * @return
     */
    public String getItemPath() {
        StringBuilder sb = new StringBuilder();
        sb.append("src//main//resources//sprites//");
        sb.append(item);
        sb.append(".gif");
        return sb.toString();
    }

    /**
     * Returns the type of special item or zero.
     * @return
     */
    public int getEnd() {
        if (this.item.equals("cherry")) {
            return 30;
        }
        if (this.item.equals("strawberry")) {
            return 50;
        } else {
            return 0;
        }
    }
}
