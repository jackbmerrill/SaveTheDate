package cs3500.provider.view;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.ICentralSystem;
import cs3500.calendar.view.ICentralSystemFrame;
import cs3500.provider.controller.FeatureAdapter;

/**
 * Adapter class to adapt from the provider view to our ICentralSystemFrame interface.
 * By doing so, we are able to pass in their view to our controller and run their view
 * using our packages. Composes the provider view. Not all methods work as they did
 * not implement them in their view.
 */

public class CentralFrameAdapter implements ICentralSystemFrame {

  private final CalendarUI view;
  private final ICentralSystem model;

  /**
   * Constructor for the adapter. Takes in our model and adapts it for use in their
   * view by using our feature adapter class.
   * @param model our model
   */
  public CentralFrameAdapter(ICentralSystem model) {
    this.view = new CalendarUI(new FeatureAdapter.ReadOnlyModelAdapter(model));
    this.model = model;
  }

  @Override
  public void makeVisible(boolean visible) {
    this.view.setVisible(visible);
  }

  @Override
  public void setFeature(IFeatures feature) {
    this.view.setFeatures(new FeatureAdapter(feature, model));
  }

  @Override
  public void createErrorBox(String message) {
    return;
  }

  @Override
  public void refresh() {
    this.view.render();
  }
}
