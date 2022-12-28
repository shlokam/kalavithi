const showLogInPopUp = () => ({ type: "SHOWLOGIN" });
const hideLogInPopUp = () => ({ type: "HIDELOGIN" });

const showRegisterPopUp = () => ({ type: "SHOWREGISTER" });
const hideRegisterPopUp = () => ({ type: "HIDEREGISTER" });

const showModal = () => ({ type: "SHOWMODAL" });
const hideModal = () => ({ type: "HIDEMODAL" });

const showCarousel = () => ({ type: "SHOWCAROUSEL" });
const hideCarousel = () => ({ type: "HIDECAROUSEL" });

const showChangePasswordPopUp = () => ({ type: "SHOWCHANGEPASSWORDMODAL" });
const hideChangePasswordPopUp = () => ({ type: "HIDECHANGEPASSWORDMODAL" });

const showLogoutPopUp = () => ({ type: "SHOWLOGOUTMODAL" });
const hideLogoutPopUp = () => ({ type: "HIDELOGOUTMODAL" });

const showLikeImage = () => ({ type: "SHOWLIKEIMAGEMODAL" });
const hideLikeImage = () => ({ type: "HIDELIKEIMAGEMODAL" });

const showFavouriteImage = () => ({ type: "SHOWFAVOURITEIMAGEMODAL" });
const hideFavouriteImage = () => ({ type: "HIDEFAVOURITEIMAGEMODAL" });


const actions = {
    showLogInPopUp,
    hideLogInPopUp,
    showRegisterPopUp,
    hideRegisterPopUp,
    showModal,
    hideModal,
    showCarousel,
    hideCarousel,
    showChangePasswordPopUp,
    hideChangePasswordPopUp,
    showLogoutPopUp,
    hideLogoutPopUp,
    showLikeImage,
    hideLikeImage,
    showFavouriteImage,
    hideFavouriteImage
};


export default actions;
