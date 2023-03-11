export default {
  CONFIG: {
    url: 'http://' + (process.env.VUE_APP_FIGHT_HOST ?? 'localhost') + ':8888',
    realm: 'super_realm',
    clientId: 'frontend'
    // "realm": "super_realm",
    // "auth-server-url": "http://localhost:8888/",
    // "ssl-required": "external",
    // "clientId": "frontend",
    // "public-client": true,
    // "confidential-port": 0
  }
}

//
