package by.training.controller.rest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import by.training.constants.EntityConstants.Structure;
import by.training.entity.AbstractEntity;
import by.training.entity.UserEntity;

public class RestController {

    public static final String JSON_EXT = ".json";

    public UserEntity getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = auth.getPrincipal();
        return (UserEntity) user;
    }

    public List<Long> getIdList(final String json) {
        List<Long> ids = new LinkedList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            long id = jsonObject.getLong(Structure.AbstractFields.ID);
            ids.add(id);
        }
        return ids;
    }

    public <T extends AbstractEntity> ResponseEntity<Object> checkEntity(final T entity) {
        if (entity == null) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    public <T extends AbstractEntity> ResponseEntity<List<T>> checkEntity(final List<T> entity) {
        if (entity == null) {
            return new ResponseEntity<List<T>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<T>>(entity, HttpStatus.OK);
    }

}
