#mylyn-tbg


This is a Eclipse Mylyn connector for The Bug Genie bug tracker, modelled after the bitbucket plugin written by Shuji Watanabe and Peter Lupo. It is now in the early stage of development, has very limited features, it's probably full of bugs and there is no release yet. YMMV.

master branch currently works against [my fork](https://github.com/guidograzioli/thebuggenie) of TBG, while 3.2 is abandoned and just for reference.

##STATUS

- Repository configuration and authentication (ok).
- Query wizard (ok).
- Task editor (can view issues, create new issues, no updates or deletions).

##TODO

- Load comments.
- Support query with multiple issuetype values (done).
- Support metadata and custom fields.
- File attachments (make screenshot like mantis-bt connector).
- Support proxy (read and use eclipse settings, use with apache httpclient).

##INSTALLATION

- Clone and install [my fork](https://github.com/guidograzioli/thebuggenie) of TBG with configuration as usual.
- Add some projects and issues.
- Setup an eclipse PDE environment (you may want PDT and xdebug for contributing to the connector, TBG side).
- Clone this git repo and import in eclipse as plugin projects.
- Launch com.undebugged.mylyn.tbg.core as eclipse application.
