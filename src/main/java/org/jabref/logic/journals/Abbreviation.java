package org.jabref.logic.journals;

import java.io.Serializable;
import java.util.Objects;

public class Abbreviation implements Comparable<Abbreviation>, Serializable {

    private static final long serialVersionUID = 1;

    private transient String name;
    private final String abbreviation;
    private transient String dotlessAbbreviation;

    // Is the empty string if not available
    private String shortestUniqueAbbreviation;

    /*
     * JBR-2
     */
    public Abbreviation(String name, String abbreviation) {
        this(name, abbreviation, "");
    }

    /*
     * JBR-2
     */
    public Abbreviation(String name, String abbreviation, String shortestUniqueAbbreviation) {
        this(name,
                abbreviation,
                // "L. N." becomes "L  N ", we need to remove the double spaces inbetween
                abbreviation.replace(".", " ").replace("  ", " ").trim(),
                shortestUniqueAbbreviation.trim());
    }

    /*
     * JBR-2
     */
    private Abbreviation(String name, String abbreviation, String dotlessAbbreviation, String shortestUniqueAbbreviation) {
        this.name = name.intern();
        this.abbreviation = abbreviation.intern();
        this.dotlessAbbreviation = dotlessAbbreviation.intern();
        this.shortestUniqueAbbreviation = shortestUniqueAbbreviation.trim().intern();
    }

    /*
     * JBR-2
     */
    public String getName() {
        return name;
    }

    /*
     * JBR-2
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /*
     * JBR-2
     */
    public String getShortestUniqueAbbreviation() {
        if (shortestUniqueAbbreviation.isEmpty()) {
            shortestUniqueAbbreviation = getAbbreviation();
        }
        return shortestUniqueAbbreviation;
    }

    /*
     * JBR-2
     */
    public boolean isDefaultShortestUniqueAbbreviation() {
        return (shortestUniqueAbbreviation.isEmpty()) || this.shortestUniqueAbbreviation.equals(this.abbreviation);
    }

    /*
     * JBR-2
     */
    public String getDotlessAbbreviation() {
        return this.dotlessAbbreviation;
    }

    /*
     * JBR-2
     */
    @Override
    public int compareTo(Abbreviation toCompare) {
        int nameComparison = getName().compareTo(toCompare.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }

        int abbreviationComparison = getAbbreviation().compareTo(toCompare.getAbbreviation());
        if (abbreviationComparison != 0) {
            return abbreviationComparison;
        }

        return getShortestUniqueAbbreviation().compareTo(toCompare.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    public String getNext(String current) {
        String currentTrimmed = current.trim();

        if (getDotlessAbbreviation().equals(currentTrimmed)) {
            return getShortestUniqueAbbreviation().equals(getAbbreviation()) ? getName() : getShortestUniqueAbbreviation();
        } else if (getShortestUniqueAbbreviation().equals(currentTrimmed) && !getShortestUniqueAbbreviation().equals(getAbbreviation())) {
            return getName();
        } else if (getName().equals(currentTrimmed)) {
            return getAbbreviation();
        } else {
            return getDotlessAbbreviation();
        }
    }

    /*
     * JBR-2
     */
    @Override
    public String toString() {
        return String.format("Abbreviation{name=%s, abbreviation=%s, dotlessAbbreviation=%s, shortestUniqueAbbreviation=%s}",
                this.name,
                this.abbreviation,
                this.dotlessAbbreviation,
                this.shortestUniqueAbbreviation);
    }

    /*
     * JBR-2
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Abbreviation that = (Abbreviation) o;
        return getName().equals(that.getName()) && getAbbreviation().equals(that.getAbbreviation()) && getShortestUniqueAbbreviation().equals(that.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAbbreviation(), getShortestUniqueAbbreviation());
    }
}
