package com.example.recommendapp.service;

import com.example.recommendapp.dto.UserRequestDto;
import com.example.recommendapp.dto.UserResponseDto;
import com.example.recommendapp.entity.User;
import com.example.recommendapp.mapper.UserMapper;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final FirebaseDatabase firebaseDatabase;

    public UserResponseDto addUser(UserRequestDto candidate) {

        if (isUsernameExists(candidate.userName()).join()) {
            throw new RuntimeException("Username already exists");
        }

        DatabaseReference usersRef = firebaseDatabase.getReference("users");

        String userId = usersRef.push().getKey();

        User user = mapper.userRequestDtoToUser(candidate);
        user.setId(userId);


        usersRef.child(userId).setValueAsync(user);

        return mapper.userToUserResponseDto(user);
    }

    public CompletableFuture<List<UserResponseDto>> getAllUsers() {
        DatabaseReference usersRef = firebaseDatabase.getReference("users");
        CompletableFuture<List<UserResponseDto>> future = new CompletableFuture<>();

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<UserResponseDto> userList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        UserResponseDto responseDto = mapper.userToUserResponseDto(user);
                        userList.add(responseDto);
                    }
                }
                future.complete(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    private CompletableFuture<Boolean> isUsernameExists(String username) {
        DatabaseReference usersRef = firebaseDatabase.getReference("users");
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        usersRef.orderByChild("userName").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        future.complete(dataSnapshot.exists());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        future.completeExceptionally(databaseError.toException());
                    }
                });

        return future;
    }
}