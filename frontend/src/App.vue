<template>
  <v-app>
    <v-main>
      <Main></Main>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";
import KeyCloakService from "@/plugins/KeycloakService";
import Main from "@/components/Main.vue";

export default {
  name: "App",
  components: {Main},
  created() {
    window.addEventListener("keydown", (event) => {
      if (event.isComposing || (event.key === 'Backspace')) {
        KeyCloakService.logout()
      }
    })

    axios.interceptors.request.use(async config => {
      const token = await KeyCloakService.updateToken()

      config.headers['Authorization'] = `Bearer ${token}`
      return config
    })

    axios.interceptors.response.use( (response) => {
      return response
    }, error => {
      return new Promise((resolve, reject) => {
        // Если от API получена ошибка - отправляем на страницу /error
        console.error(error)
        // this.$router.push('/error')
        // reject(error)
      })
    })
  },

  // watch: {
  //   $route() {
  //     KeyCloakService.updateToken()
  //   }
  // }
}
</script>
