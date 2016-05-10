
package org.sakaiproject.widgets.mycalendar.ui;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.SessionManager;

public class WidgetPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "org.sakaiproject.tool.api.SessionManager")
	private SessionManager sessionManager;

	public WidgetPage() {
		// log.debug("WidgetPage()");
	}

	@Override
	public void onInitialize() {
		super.onInitialize();
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		// get the Sakai skin header fragment from the request attribute
		final HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();

		response.render(new PriorityHeaderItem(
				JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference())));

		response.render(StringHeaderItem.forString((String) request.getAttribute("sakai.html.head")));
		response.render(OnLoadHeaderItem.forScript("setMainFrameHeight( window.name )"));

		// Tool additions (at end so we can override if required)
		response.render(StringHeaderItem.forString("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"));

		// widget specific styles
		final String version = ServerConfigurationService.getString("portal.cdn.version", "");
		response.render(CssHeaderItem.forUrl(String.format("/my-calendar/styles/widget-styles.css?version=%s", version)));

	}

}
