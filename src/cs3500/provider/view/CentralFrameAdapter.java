package cs3500.provider.view;

import cs3500.calendar.controller.IFeatures;
import cs3500.calendar.model.ICentralSystem;
import cs3500.calendar.view.ICentralSystemFrame;
import cs3500.provider.controller.FeatureAdapter;

public class CentralFrameAdapter implements ICentralSystemFrame {

  private final CalendarUI view;
  private final ICentralSystem model;

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

  }

  @Override
  public void refresh() {
    this.view.render();
  }
}
