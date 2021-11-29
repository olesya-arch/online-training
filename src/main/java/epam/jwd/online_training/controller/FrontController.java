package epam.jwd.online_training.controller;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.CommandFactory;
import epam.jwd.online_training.connection.ConnectionPool;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class FrontController extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(FrontController.class);
    private static final String EXECUTING_COMMAND_EXCEPTION = "Exception occurred during executing command: ";
    private static final String PROCESS_REQUEST_PROBLEM = "Problem when process request: ";
    private static final String COMMAND_NAME_PROBLEM = "Wrong or empty command name!";
    private static CommandFactory commandFactory = new CommandFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);

        Command command = commandFactory.initCommand(requestContent);
        if (command != null) {
            try {
                ActionResult actionResult = command.execute(requestContent);
                requestContent.insertAttributes(request);
                String page = actionResult.getPage();
                NavigationType navigationType = actionResult.getNavigationType();
                if (navigationType == NavigationType.FORWARD) {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                    requestDispatcher.forward(request, response);
                } else if (navigationType == NavigationType.REDIRECT) {
                    response.sendRedirect(page);
                }
            } catch (CommandException e) {
                LOG.error(EXECUTING_COMMAND_EXCEPTION);
                throw new ServletException(PROCESS_REQUEST_PROBLEM, e);
            }
        } else {
            LOG.error(EXECUTING_COMMAND_EXCEPTION);
            throw new ServletException(COMMAND_NAME_PROBLEM);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.terminatePool();
        super.destroy();
    }
}
