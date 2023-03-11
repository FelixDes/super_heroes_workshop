export default {
  CONFIG: {
    "realm": "super_realm",
    "auth-server-url": "http://" + (process.env.VUE_APP_FIGHT_HOST ?? "localhost") + ":8888/",
    "ssl-required": "external",
    "resource": "frontend",
    "public-client": true,
    "confidential-port": 0
  }
}
