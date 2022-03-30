package com.karys.jtools.menu;

import javafx.scene.Parent;
import org.springframework.core.Ordered;

public interface Menu extends Ordered {

    String getName();

    Parent getParent();

    void clean();
}
