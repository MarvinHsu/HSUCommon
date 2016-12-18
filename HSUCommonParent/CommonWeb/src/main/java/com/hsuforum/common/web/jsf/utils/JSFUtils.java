package com.hsuforum.common.web.jsf.utils;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * JSF Utilitys
 *
 */
public class JSFUtils {

	private final static Log logger = LogFactory.getLog(JSFUtils.class);

	private final static String KEY_CURRENT_PHASE_ID = "currentPhaseId";

	/**
	 * Get FacesContext
	 * 
	 * @return FacesContext
	 */
	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Get RequestScope
	 * 
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getRequestScope() {
		return (Map<String, Object>) getFacesValue("requestScope");
	}

	/**
	 * Get SessionScope
	 * 
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSessionScope() {
		return (Map<String, Object>) getFacesValue("sessionScope");
	}

	/**
	 * Get ApplicationScope
	 * 
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getApplicationScope() {
		return (Map<String, Object>) getFacesValue("applicationScope");
	}

	/**
	 * Get RequestParam
	 * 
	 * @return Map<String, Object>
	 */

	public static Map<String, String> getRequestParam() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params;
	}

	/**
	 * Get request param value by key
	 * 
	 * @param key
	 * @return value
	 */
	public static String getRequestParamValue(final String key) {
		return (String) getRequestParam().get(key);
	}

	/**
	 * Get locale
	 * 
	 * @return Locale
	 */
	public static Locale getRequestLocale() {
		return getFacesContext().getViewRoot().getLocale();
	}

	/**
	 * Get JSF UIViewRoot
	 * 
	 * @return UIViewRoot
	 */
	public static UIViewRoot getViewRoot() {
		return getFacesContext().getViewRoot();
	}

	/**
	 * Get HttpServletRequest
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Get HttpSession
	 * 
	 * @return HttpSession
	 */
	public static HttpSession getHttpSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	/**
	 * Get viewId
	 * 
	 * @return viewId
	 */
	public static String getViewId() {
		final UIViewRoot viewRoot = getViewRoot();
		String result = null;
		if (viewRoot != null) {
			result = viewRoot.getViewId();
		}
		return result;
	}

	/**
	 * Get managed bean by name
	 * 
	 * @param name
	 *            managed bean name
	 * @return managed bean
	 */
	public static Object getManagedBean(final String name) {
		return getFacesValue(name);
	}

	/**
	 * Set Managed Bean
	 * 
	 * @param name
	 *            名稱
	 * @param obj
	 *            Managed Bean物件
	 */
	public static void setManagedBean(final String name, final Object obj) {
		setFacesValue(name, obj);
	}

	/**
	 * Get el bind jsf value object
	 * 
	 * @param key
	 * @return value object
	 */
	public static Object getFacesValue(final String key) {

		return getFacesContext().getApplication().getExpressionFactory()
				.createValueExpression(getFacesContext().getELContext(), "#{" + key + "}", Object.class)
				.getValue(getFacesContext().getELContext());
	}

	/**
	 * Get el所綁定的JSF Faces Method
	 * 
	 * @param key
	 * @return method Expression
	 */
	public static Object getFacesMethodExpression(final String key) {

		return getFacesContext().getApplication().getExpressionFactory().createMethodExpression(
				getFacesContext().getELContext(), "#{" + key + "}", String.class, new Class[] {});
	}

	/**
	 * Get el所綁定的JSF Faces Method Expression Listener
	 * 
	 * @param key
	 * @return Faces Method Expression Listener
	 */
	public static Object getFacesMethodExpressionListener(final String key) {

		return getFacesContext().getApplication().getExpressionFactory().createMethodExpression(
				FacesContext.getCurrentInstance().getELContext(), "#{" + key + "}", String.class,
				new Class[] { ActionEvent.class });
	}

	/**
	 * Get Faces所Set 的value
	 * 
	 * @param key
	 * @param obj
	 */
	public static void setFacesValue(final String key, final Object obj) {
		getFacesContext().getApplication().getExpressionFactory()
				.createValueExpression(getFacesContext().getELContext(), "#{" + key + "}", Object.class)
				.setValue(getFacesContext().getELContext(), obj);

	}

	/**
	 * <p>
	 * Return the {@link UIComponent} (if any) with the specified
	 * <code>id</code>, searching recursively starting at the specified
	 * <code>base</code>, and examining the base component itself, followed by
	 * examining all the base component's facets and children. Unlike
	 * findComponent method of {@link UIComponentBase}, which skips recursive
	 * scan each time it finds a {@link NamingContainer}, this method examines
	 * all components, regardless of their namespace (assuming IDs are unique).
	 *
	 * @param base
	 *            Base {@link UIComponent} from which to search
	 * @param id
	 *            Component identifier to be matched
	 */

	public static UIComponent findComponent(final UIComponent base, final String id) {
		UIComponent result = null;
		// Is the "base" component itself the match we are looking for?
		if (id.equals(base.getId())) {
			result = base;
		} else {
			// Search through our facets and children
			UIComponent kid = null;
			@SuppressWarnings("rawtypes")
			final Iterator kids = base.getFacetsAndChildren();
			while (kids.hasNext() && (result == null)) {
				kid = (UIComponent) kids.next();
				if (id.equals(kid.getId())) {
					result = kid;
					break;
				}
				result = findComponent(kid, id);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Get JSF UIComponent
	 * 
	 * @param id
	 * @return UIComponent
	 */
	public static UIComponent findComponentInRoot(final String id) {
		UIComponent ret = null;

		final FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			ret = findComponent(context.getViewRoot(), id);
		}

		return ret;
	}

	/**
	 * Get Client Id
	 * 
	 * @param id
	 * @return Client Id
	 */
	public static String getClientId(final String id) {
		final UIComponent component = JSFUtils.findComponentInRoot(id);
		String result = null;
		if (component == null) {
			logger.info("cannot find component for id: " + id);
		} else {
			result = component.getClientId(FacesContext.getCurrentInstance());
		}
		return result;
	}

	/**
	 * 系統可能一開始的 entry point 是一個 servlet 但是要和後面的 jsf managed bean 互動, ex: 做參數的
	 * initialzation 此 method 接收 request 和 response, 來初始化 FacesContext 讓 servelt
	 * 可以Get 到 相關的 managed bean
	 *
	 * @param request
	 * @param response
	 */
	public static void initFacesContext(final HttpServletRequest request, final HttpServletResponse response,
			final ServletContext servletContext) {

		final LifecycleFactory lFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		final Lifecycle lifecycle = lFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
		final FacesContextFactory fcFactory = (FacesContextFactory) FactoryFinder
				.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		final FacesContext facesContext = fcFactory.getFacesContext(servletContext, request, response, lifecycle);
		final Application application = facesContext.getApplication();
		final ViewHandler viewHandler = application.getViewHandler();

		// specify what page you want to pretend to "come from"; normally
		// an empty string is ok
		final String viewId = "";
		final UIViewRoot view = viewHandler.createView(facesContext, viewId);
		facesContext.setViewRoot(view);

	}

	/**
	 * Set JSF Current Phase Id
	 * 
	 * @param phaseId
	 */
	public static void setCurrentPhaseId(PhaseId phaseId) {
		JSFUtils.getRequestScope().put(KEY_CURRENT_PHASE_ID, phaseId);
	}

	/**
	 * Get JSF Current Phase Id
	 * 
	 * @return
	 */
	public static PhaseId getCurrentPhaseId() {
		return (PhaseId) JSFUtils.getRequestScope().get(KEY_CURRENT_PHASE_ID);
	}

	/**
	 * Get ServletContext
	 * 
	 * @return ServletContext
	 */
	public static ServletContext getServletContext() {
		return JSFUtils.getHttpSession().getServletContext();
	}

	/**
	 * Get RealPath
	 * 
	 * @return
	 */
	public static String getRealPath() {
		return getRealPath("/");
	}

	/**
	 * Get Servlet Real Path
	 * 
	 * @param path
	 * @return Servlet Real Path
	 */
	public static String getRealPath(final String path) {
		return getServletContext().getRealPath(path);
	}

	/**
	 * Get Servlet ContextPath
	 * 
	 * @return Servlet ContextPath
	 */
	public static String getContextPath() {
		return getHttpServletRequest().getContextPath();
	}
}
