REDWOOD LOGGER
==============

Authors:
-  Gabor Angeli (`gangeli`)
-  David McClosky (`dmcc`)

Redwood was developed at Stanford by students in the Stanford NLP Group.
A tutorial in `docs/tutorial.pdf`, presented to the group, provides a good
introduction to the theory and use of Redwood. It also motivates what types
of projects would fit well into the paradigm.

Redwood is a hierarchical channel based logger. Log messages are arranged
hierarchically by depth (e.g. `main->tagging->sentence 2`) using the `startTrack()`
and `endTrack()` methods. Furthermore, messages can be flagged with a number of
channels, which allow filtering by channel. Log levels are implemented as
channels (`ERROR`, `WARNING`, etc).

Details on the handlers used are documented in their respective classes, which
all implement `LogRecordHandler`. New handlers should implement this class.

Details on configuring Redwood can be found in the `RedwoodConfiguration` class.
New configuration methods should be implemented in this class, following the
standard builder paradigm.


Code Examples
-------------

### Hello World
Logging `Hello World` through Redwood is a simple as we could make it:

     import edu.stanford.nlp.util.logging.Redwood;
     Redwood.log("Hello World!");
     
     >> Hello World!
     
### Redwood.Util
Java's `import static` functionality allows us to be even more brief than the Hello World
example above.
The `Redwood.Util` class has useful shorthands for the public methods in Redwood,
as well as some additional utilities.
To illustrate, `Hello World` becomes:

    import static edu.stanford.nlp.util.logging.Redwood.Utils.*;
    log("Hello World!");
    
    >> Hello World!
    
### Tracks
Redwood's philosophy is that [certain applications of] logging should trace code
execution, and code is inherently hierarchical. Thus, Redwood encourages organizing
logging statements into `track`s. This is done via the `startTrack()` and `endTrack()` methods.
For example:

    Redwood.startTrack("Running EM");
    for (int iter = 1; iter <= 2; ++iter) {
      Redwood.logf("Iteration %i ended with value %f", iter, 1000 / iter);
    }
    Redwood.endTrack("Running EM");
    
    >> Running EM {
         Iteration 0 ended with value 1000
         Iteration 1 ended with value 500
       }
    
### Configuration
Redwood tries to have a sensible default configuration; however, applications often would like
to tweak the behavior in certain ways. This can be done with the `RedwoodConfiguration` class,
building up a configutaion using the builder paradigm.

For example, the following will cause similar messages written to the console to be collapsed,
and cause all messages to be written to a file. Note that this configuration is order-dependent.
Were the order reversed, the messages written to the file would be collaped as well:

    RedwoodConfiguration.current()  // also, .standard(), .empty()
        .file( "/path/to/file" )
        .collapseApproximate()
        .apply();                   // nothing happens until apply() is called