package fr.ensma.lias.jerboa.datastructures;

public enum Event {
  CREATION(EventCategories.GENERATION),
  DELETION(EventCategories.DESTRUCTION),
  NOMODIF(EventCategories.MODIFICATION),
  SPLIT(EventCategories.MODIFICATION),
  MERGE(EventCategories.MODIFICATION),
  MODIFICATION(EventCategories.MODIFICATION),
  NOEFFECT(EventCategories.MODIFICATION);

  public final EventCategories category;

  private Event(EventCategories category) {
    this.category = category;
  }

  public EventCategories getCategory() {
    return this.category;
  }

  public Boolean equalsCategory(Event other) {
    return this.getCategory().equals(other.getCategory());
  }
}
