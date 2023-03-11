export default {
  FIGHT_MICROSERVICE_GET_RANDOM: "http://" + (process.env.VUE_APP_FIGHT_HOST ?? "localhost") + ":8182/api/fights/randomfighters",
  FIGHT_MICROSERVICE_POST: "http://" + (process.env.VUE_APP_FIGHT_HOST ?? "localhost") + ":8182/api/fights",
  FIGHT_MICROSERVICE_GET_BATTLES_LOG: "http://" + (process.env.VUE_APP_FIGHT_HOST ?? "localhost") + ":8182/api/fights"
}
