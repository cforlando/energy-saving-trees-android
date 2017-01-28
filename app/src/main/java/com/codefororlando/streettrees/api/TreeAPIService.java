package com.codefororlando.streettrees.api;

import com.codefororlando.streettrees.api.models.Tree;
import com.codefororlando.streettrees.api.models.TreeDescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TreeAPIService {

    String TREE_DESCRIPTIONS_ROUTE = "69mx-t3bq.json";
    String TREES_ROUTE = "7w7p-3857.json";

    @GET(TREE_DESCRIPTIONS_ROUTE)
    Call<List<TreeDescription>> getTreeDescriptions();

    @GET(TREES_ROUTE)
    Call<List<Tree>> getTrees();

}
