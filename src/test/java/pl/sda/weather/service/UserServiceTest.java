package pl.sda.weather.service;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.sda.weather.dao.UserDao;
import pl.sda.weather.model.UserEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class UserServiceTest {

    public static final String PASSWORD = "password1";
    public static final String BAD_PASSWORD = "bad_password";

    @Test
    public void givenUserExistsWhenLoginThenAuthorize() {
        //given
        UserDao userDaoMock = mock(UserDao.class);
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(PASSWORD);
        when(userDaoMock.findByLogin(any())).thenReturn(Optional.of(userEntity));
        UserService userService = new UserService(userDaoMock, null);
        //when
        boolean loginResult = userService.login("login", PASSWORD);
        //then
        assertThat(loginResult).isTrue();
        assertThat(userService.currentUser()).isNotEmpty();
        assertThat(userService.currentUser().get()).isEqualTo(userEntity);
    }

    @Test
    public void givenUserDoesntExistWhenLoginThenDoNotAuthorize() {
        //given
        UserDao userDaoMock = mock(UserDao.class);
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(PASSWORD);
        when(userDaoMock.findByLogin(any())).thenReturn(Optional.of(userEntity));
        UserService userService = new UserService(userDaoMock, null);
        //when
        boolean loginResult = userService.login("login", BAD_PASSWORD);
        //then
        assertThat(loginResult).isFalse();
        assertThat(userService.currentUser()).isEmpty();
    }



}