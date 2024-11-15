OVERVIEW: our code can be run in the plan runner by creating a new model and passing it into
          a new object of the controller. In the command line, before the code actually runs,
          one must specify which strategy they want to use for the schedule event frame, either
          "anytime" or "workhours." For creation, simply create a new model and then pass it
          into the controller along with the desired strategy as an object.

QUICKSTART: Create a new run configuration with PlanRunner as the desired class and in
            the command line, enter the name of the desired strategy you would like to use
            to search for times to schedule events. Before running, if you would like to add
            users to the system, add them to the model. Otherwise, simply import your
            XML file to add the users and schedules. You can also create a model which
            takes in schedules you have already created.

            QUICKSTART WITH JAR: java -jar SaveTheDate.jar "any commands"
                - this is how to run the program using the jar. In "any commands", enter
                the desired strategy, whether "anytime" or "workhours". If you wish to use
                the provider model, select a strategy and then write "provider"
                    i.e. java -jar SaveTheDate.jar anytime provider

This codebase is designed to be a weekly calendar capable of managing multiple schedules
    and importing and exporting them as XMLs. Events can be added, removed, and modified,
    where all changes will be shared across all schedules with the same events.

    ORGANIZATION:
    - src : our source file
        -cs3500.
            -calendar - further package isolation - Our code base
                - model : all classes and interfaces pertaining to our model are stored here
                - xml : all xml related classes are here
                - view : our textual view and GUI classes and interfaces are here
                - controller : Controller related classes and interfaces
                - strategy : strategy related classes and interfaces
            -provider - code provided from our provider
                - model : model classes and interfaces
                - controller : controller interface and adapter
                - view : view interfaces, classes, and adapter
    - test : all test classes
        -cs3500.calendar - further package isolation
            - model - all model tests and mocks for controller
            - view - tests for the textual view
            - xml - all tests for the xml classes
            - controller - tests for the controller using mocks
            - strategy - all tests for the strategies


    KEY COMPONENTS:
    CentralSystem - main hub that houses all of the schedules
    Schedules - individual user schedules that track a users events. acts as a calendar.
    Events - events in the calendar of a user with a name, location, time and list of attendees.
        - Time - the time of the event, ie. Start day to end day, start time to end time.
                 each may last up to one week exactly.
        - Name - the name of the event as a String
        - Location - the location of the event and whether or not it is online
        - Users - the list of all users who are attending including the host.


The central system consists of a map of strings of user IDs to schedules. Whenever the schedule
    is accessed, it calls get using the userid to get the schedule. Within the schedules,
    there is a map of event names to events so to access the desired event. each action is
    delegated from central system -> schedule -> event. Each event is a shared object therefore
    each time one is modified, it modifies the event for all users with the event in their
    schedule.


Changes for Part 2:
    ADDED KEY COMPONENTS:
        CentralSystemFrame - visibly displays the calendar and allows selection of schedules
        EventFrame - enables the editing and removal of existing events, and creation of new ones
        SchedulePanel - panel within the central frame which displays the users schedules
            - each has a respective interface

        The central frame is the hub, pressing the buttons or clicking on an event in the
        calendar opens an event frame. Different users can be selected to view their schedules
        and xml can be imported and exported. Updates to the organization have been noted
        above where the organization is marked.

        NOTE: functionality has been added for listeners to be implemented in the future with
              the creation of the controller, so once that is created the lambdas of the
              buttons will be replaced with can be replaced with action commands to remove
              any coupling.

    Changes: we added two new methods in the read only central interface for get users and event
             conflict, a new method in time to convert from an int to a enumerator for dates, and
             added notes to the javadoc for event about the invariant included



Changes for part 3:
    - changed the load and save xml methods in the central system to take in a single user as
    a string rather than taking in a list of strings. Makes life easier for the controller and
    simply just does not make sense with our view as it only interacts with one user at a time.
    - Changed the listeners and add listener methods in all classes in the view to take in
    the features interface and assign it to a field in order to perform callbacks to the
    controller. Therefore, the respective methods that should output something have been connected
    to the controller methods to ensure they work as intended and pass up the desired information.
    - updated the file organization to reflect the new packages
    - Added a new implementation of the IEventFrame interface for the schedule event panel and
    hooked it up to our existing code base.
    - Changed the order in which the days are initialized in the enumerator so Sunday comes
    first always. Helps make our strategies more accurate.

    ADDED KEY COMPONENTS:
        - IFeatures interface - interface defining all actions our controller should be able to
                                do and use to interact between the view and the controller.
                                The controller only quits when the central system frame is closed.
        - Controller - implementation of the controller. Delegates any input from the view via
                       callbacks to the model and if any errors occur, the controller creates a
                       new error box in the view with the given exception message. In creation of
                       the controller, it only takes in the model and strategy, and connects it
                       to a new private view created within the constructor of the controller.
        - Strategies - define the way in which the the system will search for available times to
                       schedule an event for all the requested users. If there is no such valid
                       time, the strategy will throw an error. Each strategy is an object,
                       and the method takes in the desired info and attempts to find a time.

Changes for part 4:
                    -pre sending to customer
                        - changed all interfaces and constructors to take in interfaces rather than
                        the actual classes. Ie to make use of our code work with other
                        implementations of the interfaces and more modular. Also to allow for
                        easier creation of adapters by using interfaces rather than classes.
                        - create ITime interface to define our time class as all complex classes
                        need to have interfaces in order to make code sharing and reuse easier.

                    - After adding provider code (code from classmates to be adapted)
                        - in the command line for plan runner, simply add "provider" after the
                        strategy to use the provider view.
                        PROVIDER VIEW -
                            - they have no strategies so the schedule frame does not work in
                            their view. When the schedule frame is closed, it quits the program.
                            - Create event works as intended, used adapter to push a valid event
                            to our model and create it. However the users do not show up.
                            - Remove event does not fully work. It will remove the event, but it
                            will remove the event from everyone's schedule, regardless of who is
                            removing it as their code does not check at all.
                            - Switching schedules does not work as the way it is designed is
                            incompatible with our code. It relies on modifying the model, rather
                            than just viewing a read only version.
                            - Loading and saving schedules works as intended.
                            - There is no way of displaying errors in their view, so when an error
                            is produced, nothing happens.
                            - Modifying the event does not work. It causes an error in the
                            listeners due to the way we had to adapt their code without knowing
                            how any of it really worked. (lack of comments)

Changes for part 5:
                   For Level 0:
                        - Updated the CentralSystemFrame to add a new JButton called
                          toggleHostColor, initialized the button in the controller, and added a
                          new action listener which called a new toggleHostColor method onto the
                          schedule.
                        - Added this toggleHostColor method to the ISchedulePanel interface and
                          implemented this new method into the SchedulePanel class. When the method
                          is called the color of the host changes to either blue or the default red
                          depending on its current position.

                   For Level 1:
                        - Our model already allows for the week to start on a different day, since
                          each day is assigned to a numerical value. The view decides which day of
                          the week is assigned to which column of the calendar, since the calendar
                          only spans a singular week. Due to the flexibility of our model, we were
                          able to jump straight into Level 2.

                   For Level 2:
                        - Implemented new versions of the Central Frame and the Schedule panel
                        for when the calendar is starting from saturday. We extended our existing
                        classes, CentralSystemFrame and SchedulePanel, made the required fields,
                        and methods we needed protected so that
                        we could override and access them in order to make the calendar run. For
                        the Schedule panel, we overrode the drawEvents to draw saturday as the
                        start of the week and overrode the mouse click so it would still open
                        the desired events as before by shifting the day over. You can still
                        click on any event and it will open. For the saturday Central Frame,
                        we created a new protected method in the original central frame to set
                        the schedule so we could override it in our new class and set a saturday
                        schedule panel. Because we handled it in this way using inheritance and
                        overrides, we did not need to change any other code to get it to work.

                   For Level 3:
                        - Once again, because our model does not rely on the ordering of the days
                          and our strategy classes implement a read only instance of our model,
                          our workhours strategy does not need to be adapted for the new saturday
                          first calendar and works the same. However for the anytime strategy,
                          we created a new strategy so the first available time would be saturday
                          first, and then sunday. We did so by simply implementing our strategy
                          interface and implementing the method. We did not find any need to
                          abstract anything as the method in each strategy is dependent on the
                          accessible fields.
