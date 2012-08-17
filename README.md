REDWOOD LOGGER
==============

Authors:
-  Gabor Angeli (gangeli)
-  David McClosky (dmcc)

Redwood was developed at Stanford by students in the Stanford NLP Group.
A tutorial in docs/tutorial.pdf, presented to the group, provides a good
introduction to the theory and use of Redwood. It also motivates what types
of projects would fit well into the paradigm.

Redwood is A hierarchical channel based logger. Log messages are arranged
hierarchically by depth (e.g. main->tagging->sentence 2) using the startTrack()
and endTrack() methods. Furthermore, messages can be flagged with a number of
channels, which allow filtering by channel. Log levels are implemented as
channels (ERROR, WARNING, etc).

Details on the handlers used are documented in their respective classes, which
all implement LogRecordHandler. New handlers should implement this class.

Details on configuring Redwood can be found in the RedwoodConfiguration} class.
New configuration methods should be implemented in this class, following the
standard builder paradigm.
