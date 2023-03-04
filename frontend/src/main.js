/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Components
import App from './App.vue'

// Composables
import {createApp} from 'vue'

// Plugins
import {registerPlugins} from '@/plugins'
// import KeyCloakService from "@/plugins/KeycloakService";

const app = createApp(App)

registerPlugins(app)

const renderApp = () => {
  app.mount("#app");
};
renderApp()
// KeyCloakService.callLogin(renderApp);
