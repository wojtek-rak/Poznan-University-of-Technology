package sample.Actors.Messages;

//Message
public class ReadApiMessage {
    public final String username;
    public final String repositoryName;

    public ReadApiMessage(String name, String repoName)
    {
        username = name;
        repositoryName = repoName;
    }
}
