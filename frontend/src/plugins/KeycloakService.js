import Keycloak from "keycloak-js";
const keycloakInstance = new Keycloak();
/**
 * Initializes Keycloak instance and calls the provided callback function if successfully authenticated.
 *
 * @param onAuthenticatedCallback
 */
const login = (onAuthenticatedCallback) => {
  keycloakInstance
    .init({ onLoad: "login-required" })
    .then(function (authenticated) {
      authenticated ? onAuthenticatedCallback() : alert("non authenticated");
    })
    .catch((e) => {
      console.dir(e);
      console.log(`keycloak init exception: ${e}`);
    });
};

const updateToken = async () => {
  await keycloakInstance.updateToken(70)
  return keycloakInstance.token
}

const logout = () => {
  return keycloakInstance.logout()
}

const checkRole = (role) => {
  return keycloakInstance.hasRealmRole(role)
}

const KeyCloakService = {
  callLogin: login,
  updateToken: updateToken,
  logout: logout,
  checkRole: checkRole,
};
export default KeyCloakService;
