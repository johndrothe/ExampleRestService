package work.rothe.branch.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static work.rothe.branch.samples.Samples.sampleDto;
import static work.rothe.branch.samples.Samples.sampleRepo;
import static work.rothe.branch.samples.Samples.sampleRepoDto;
import static work.rothe.branch.samples.Samples.sampleRepoDtos;
import static work.rothe.branch.samples.Samples.sampleRepos;
import static work.rothe.branch.samples.Samples.sampleUser;

public class GitHubUserConverterTest {
    private final GitHubUserConverter converter = new GitHubUserConverter();

    @Test
    public void convertUser() {
        assertEquals(sampleDto(), converter.convertUser(sampleUser(), sampleRepos()));
    }

    @Test
    public void convertRepos() {
        assertEquals(sampleRepoDtos(), converter.convertRepos(sampleRepos()));
    }

    @Test
    public void convertRepo() {
        assertEquals(sampleRepoDto(), converter.convertRepo(sampleRepo()));
    }
}
