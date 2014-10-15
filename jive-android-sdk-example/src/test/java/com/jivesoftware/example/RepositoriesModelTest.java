package com.jivesoftware.example;

import com.jivesoftware.example.github.IGitHubRepoService;
import com.jivesoftware.example.github.dao.Organization;
import com.jivesoftware.example.github.dao.Repository;
import com.jivesoftware.example.listenable.TypeListenable;
import com.jivesoftware.example.repositories.RepositoriesModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import retrofit.Callback;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by stephen.mclaughry on 9/23/14.
 */
public class RepositoriesModelTest {

    private RepositoriesModel testObject;

    @Mock
    private IGitHubRepoService repoService;
    @Mock
    private TypeListenable typeListenable;
    @Mock
    private Organization organization;
    @Mock
    private Repository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        testObject = new RepositoriesModel(repoService, typeListenable);
    }

    @Test
    public void shouldPostRepositoriesOnRefreshOrganizations() {
        testObject.refreshOrganizations();

        verify(repoService).getOrganizations(any(Callback.class));
    }
}
