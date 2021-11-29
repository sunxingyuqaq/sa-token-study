package com.study.boot.quicklogin.config.log;

import com.study.boot.quicklogin.config.LoggerQueue;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

/**
 * describe:
 *
 * @author admin
 * @date 2021/11/23 09:42
 */
@Plugin(name = "webSocketAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class WebSocketAppender extends AbstractAppender {

    private LoggerQueue loggerQueue = LoggerQueue.getInstance();

    protected WebSocketAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static WebSocketAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                   @PluginElement("Layout") Layout layout,
                                                   @PluginElement("Filters") Filter filter) {
        if (name == null) {
            LOGGER.error("No name provided for WebSocketAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new WebSocketAppender(name, filter, layout, ignoreExceptions, Property.EMPTY_ARRAY);
    }

    @Override
    public void append(LogEvent event) {
        loggerQueue.push(new String(getLayout().toByteArray(event)));
    }
}
