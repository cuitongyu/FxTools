package com.fx.tool.common.component;

import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public final class ChildFrame extends Stage {
    private static ChildFrame frame = null;

    private ChildFrame() {
    }

    private static void init(ChildFrame childFrame) {
        childFrame.initStyle(StageStyle.DECORATED);
        childFrame.setResizable(false);
        childFrame.initModality(Modality.APPLICATION_MODAL);
        childFrame.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                ChildFrame.frame = null;
            }
        });
    }

    public static final ChildFrame newInstance() {
        if (frame == null) {
            frame = new ChildFrame();
            init(frame);
        }

        return frame;
    }

    public static final ChildFrame create() {
        ChildFrame newFrame = new ChildFrame();
        init(newFrame);
        return newFrame;
    }
}

