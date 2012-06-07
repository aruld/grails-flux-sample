import sample.flux.scheduler.JobScheduler

class BootStrap {

    def init = { servletContext ->
        JobScheduler.getInstance().startup()
    }
    def destroy = {
        JobScheduler.getInstance().shutdown()
    }
}
