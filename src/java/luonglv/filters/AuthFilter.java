/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luonglv.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import luonglv.dtos.AccountDTO;

/**
 *
 * @author LeVaLu
 */
public class AuthFilter implements Filter {

    private static final String HOME = "index.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String CUSTOMER = "index.jsp";
    private static final String PAGE_NOT_FOUND = "page_not_found.jsp";
    private static final String ROLE_NOT_SUPPORT = "role_not_support.jsp";

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private final List<String> guestResources;
    private final List<String> customerResources;
    private final List<String> adminResources;

    public AuthFilter() {
        /**
         * Grant resources for guess
         */
        guestResources = new ArrayList();
        // Page resources for guess
        guestResources.add("");
        guestResources.add("index.jsp");
        guestResources.add("login.jsp");
        guestResources.add("error.jsp");
        guestResources.add("customer_register.jsp");
        // Controller resources for guess
        guestResources.add("login");
        guestResources.add("logout");
        guestResources.add("register");
        guestResources.add("loadAllHotel");
        guestResources.add("searchHotel");

        /**
         * Grant resources for customer
         */
        customerResources = new ArrayList();
        // Page resources for customer
        customerResources.add("");
        customerResources.add("index.jsp");
        customerResources.add("error.jsp");
        customerResources.add("customer_cart.jsp");
        customerResources.add("customer_checkout.jsp");
        customerResources.add("customer_confirm.jsp");
        customerResources.add("customer_history.jsp");
        // Controller resources for customer
        customerResources.add("logout");
        customerResources.add("loadAllHotel");
        customerResources.add("searchHotel");
        customerResources.add("addToCart");
        customerResources.add("deleteInCart");
        customerResources.add("updateInCart");
        customerResources.add("checkout");
        customerResources.add("loadInfo");
        customerResources.add("checkCoupon");
        customerResources.add("checkout");
        customerResources.add("loadHistory");
        customerResources.add("deleteHistory");
        customerResources.add("searchHistory");
        customerResources.add("createPayment");
        customerResources.add("executePayment");

        /**
         * Grant resources for admin
         */
        adminResources = new ArrayList();
        // Page resources for admin
        adminResources.add("admin.jsp");
        adminResources.add("error.jsp");
        adminResources.add("admin_register.jsp");
        // Controller resources for admin
        adminResources.add("logout");
        adminResources.add("loadAllHotel");
        adminResources.add("searchHotel");
        adminResources.add("adminRegister");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String uri = req.getRequestURI();

            if (uri.contains("/css") || uri.contains("/js") || uri.contains("/assets")) {
                chain.doFilter(request, response);
            } else {
                // Retrive resouces
                int lastIndex = uri.lastIndexOf("/");
                String resource = uri.substring(lastIndex + 1);

                // Get session
                HttpSession session = req.getSession(false);

                // If there is no session or no session's USER_ROLE attribute
                if (session == null || session.getAttribute("ACCOUNT") == null) {
                    if (guestResources.contains(resource)) {
                        chain.doFilter(request, response);
                    } // if guest click to cart button, redirect to login page
                    else if (resource.equals("addToCart")) {
                        try (PrintWriter out = res.getWriter()) {
                            out.write("redirect");
                        }
                    } else {
                        res.sendRedirect(HOME);
                    }
                } else {
                    // Get Account from session
                    AccountDTO accountDTO = (AccountDTO) session.getAttribute("ACCOUNT");
                    /**
                     * Check User's authentication
                     */
                    // Check if there are no match resources, redirect to page_not_found
                    if (!guestResources.contains(resource)
                            && !customerResources.contains(resource)
                            && !adminResources.contains(resource)) {
                        res.sendRedirect(PAGE_NOT_FOUND);
                    } else {
                        // Check if account's role is admin
                        if (accountDTO.getRoleName().equals("Admin")) {
                            if (adminResources.contains(resource)) {
                                chain.doFilter(request, response);
                            } else {
                                res.sendRedirect(ADMIN);
                            }
                        } // Check if account's role is customer
                        else if (accountDTO.getRoleName().equals("Customer")) {
                            if (customerResources.contains(resource)) {
                                chain.doFilter(request, response);
                            } else {
                                res.sendRedirect(CUSTOMER);
                            }
                        } else {
                            res.sendRedirect(ROLE_NOT_SUPPORT);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            log("Error at AuthFilter: " + t.getMessage());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
