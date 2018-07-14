var config = {
  project: 'jsuk-server',
  outDir: 'bundle',
  tmpDir: '/tmp/jsuk-server',
  deployDir: '/jsuk',
  buildCmd:['mvnw clean install -DskipTests=true'],
  files: {
    'jsuk-server': ['target/*.jar'],
  },
  configs: [{
    env: 'prod',
    user: 'root',
    middleServer: '47.99.45.67',
    targetServer: ['172.16.91.130'],
    // buildCmd:['npm run build'],
  }, {
    env: 'test',
    user: 'root',
    middleServer: '192.168.0.84',
    targetServer: ['192.168.0.84'],
    // buildCmd:['npm run build'],
  }]
};

module.exports = config;

