package by.training.controller.rest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.model.UserModel;

public class RestController {

    public static final String JSON_EXT = ".json";

    public UserModel getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = auth.getPrincipal();
        return (UserModel) user;
    }

    public List<Long> getIdList(final String json) {
        List<Long> ids = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(ModelFields.ID);
            ids.add(id);
        }
        return ids;
    }

}
