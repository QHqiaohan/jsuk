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
    middleServer: '192.168.1.22',
    targetServer: ['192.168.1.25'],
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

