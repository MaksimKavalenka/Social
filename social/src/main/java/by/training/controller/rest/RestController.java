package by.training.controller.rest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.training.constants.ModelStructureConstants.ModelFields;
import by.training.model.Model;
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

    public <T extends Model> ResponseEntity<T> checkEntity(final T entity) {
        if (entity == null) {
            return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<T>(entity, HttpStatus.OK);
    }

    public <T extends Model> ResponseEntity<List<T>> checkEntity(final List<T> entity) {
        if (entity == null) {
            return new ResponseEntity<List<T>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<T>>(entity, HttpStatus.OK);
    }

}
