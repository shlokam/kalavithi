import { combineReducers } from "redux";

const HandleLoginInPopUp = (
  payload = { handleLoginInPopUp: false },
  action
) => {
  if (action.type === "SHOWLOGIN") {
    return { handleLoginInPopUp: true };
  } else if (action.type === "HIDELOGIN") {
    return { handleLoginInPopUp: false };
  }
  return { handleLoginInPopUp: payload.handleLoginInPopUp };
};

const HandleRegisterPopUp = (
  payload = { handleRegisterPopUp: false },
  action
) => {
  if (action.type === "SHOWREGISTER") {
    return { handleRegisterPopUp: true };
  } else if (action.type === "HIDEREGISTER") {
    return { handleRegisterPopUp: false };
  }
  return { handleRegisterPopUp: payload.handleRegisterPopUp };
};

const HandleImagePopup = (payload = { handleImagePopup: false }, action) => {
  if (action.type === "SHOWMODAL") {
    if (localStorage.getItem("user") === "false") {
      return { handleImagePopup: true };
    }
    return { handleImagePopup: false };
  } else if (action.type === "HIDEMODAL") {
    return { handleImagePopup: false };
  }
  return { handleImagePopup: payload.handleImagePopup };
};

const HandleChangePasswordPopUp = (
  payload = { handleChangePasswordPopup: false },
  action
) => {

  if (action.type === "SHOWCHANGEPASSWORDMODAL") {
    if (localStorage.getItem("user") === "true") {
      return { handleChangePasswordPopup: true };
    }
    return { handleChangePasswordPopup: false };
  } else if (action.type === "HIDECHANGEPASSWORDMODAL") {
    return { handleChangePasswordPopup: false };
  }
  return { handlChangePasswordPopup: payload.handleChangePasswordPopup };
};


const HandleLogoutPopUp = (payload = { handleLogoutPopUp: false }, action) => {
  if (action.type === "SHOWLOGOUTMODAL") {
    if (localStorage.getItem("user") === "true") {
      return { handleLogoutPopUp: true };
    }
    return { handleLogoutPopUp: false };
  } else if (action.type === "HIDELOGOUTMODAL") {
    return { handleLogoutPopUp: false };
  }
  return { handleLogoutPopUp: payload.handleLogoutPopUp };
};


const HandleLikeImage = (payload = { handleLikeImage: false }, action) => {
  if (action.type === "SHOWLIKEIMAGEMODAL") {
    if (localStorage.getItem("user") === "true") {
      return { handleLikeImage: true };
    }
    return { handleLikeImage: false };
  } else if (action.type === "HIDELIKEIMAGEMODAL") {
    return { handleLikeImage: false };
  }
  return { handleLikeImage: payload.handleLikeImage };
};

const HandleCarousel = (
  payload = { handleCarousel: false },
  action
) => {
  if (action.type === "SHOWCAROUSEL") {
      if (localStorage.getItem('user') === "true") { 
          return { handleCarousel: true };
      }
      return { handleCarousel: false }

  }
  else if (action.type === "HIDECAROUSEL") {
      return { handleCarousel: false }
  }
  return { handleCarousel: payload.handleCarousel };
};

const HandleFavouriteImage = (payload = { handleFavouriteImage: false }, action) => {
  if (action.type == "SHOWFAVOURITEIMAGEMODAL") {
    if (localStorage.getItem("user") === "true") {
      return { handleFavouriteImage: true };
    }
    return { handleFavouriteImage: false };
  } else if (action.type == "HIDEFAVOURITEIMAGEMODAL") {
    return { handleFavouriteImage: false };
  }
  return { handleFavouriteImage: payload.handleFavouriteImage };
};


const rootReducers = combineReducers({
  HandleLoginInPopUp: HandleLoginInPopUp,
  HandleRegisterPopUp: HandleRegisterPopUp,
  HandleImagePopup: HandleImagePopup,
  HandleChangePasswordPopup: HandleChangePasswordPopUp,
  HandleLogoutPopUp: HandleLogoutPopUp,
  HandleLikeImage: HandleLikeImage,
  HandleCarousel: HandleCarousel,
  HandleFavouriteImage:HandleFavouriteImage
});

export default rootReducers;