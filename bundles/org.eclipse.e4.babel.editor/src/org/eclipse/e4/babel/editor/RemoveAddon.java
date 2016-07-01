package org.eclipse.e4.babel.editor;


import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipselabs.e4.tapiji.logger.Log;
import org.osgi.service.event.Event;


public class RemoveAddon {


    @Inject
    @Optional
    private void subscribeTopicTagsChanged(@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event, MApplication application, EModelService modelService) {
        Object changedObj = event.getProperty(EventTags.ELEMENT);

        if (UIEvents.isREMOVE(event)) {

            if (changedObj instanceof MPartStack) {


                Log.d("CLOSE", "ssddsdsd");
            }


        }

    }
}