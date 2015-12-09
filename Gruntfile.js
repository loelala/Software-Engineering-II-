module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        // Watches files for changes and runs tasks based on the changed files
        watch: {
            gruntfile: {
                files: ['Gruntfile.js']
            },
            js: {
                files: ['src/main/webapp/static/js/**/*.js'],
                tasks: ['copy:js']
            },
            fonts: {
                files: ['src/main/webapp/static/fronts/**/*'],
                tasks: ['copy:fonts']
            },
            partials: {
                files: ['src/main/webapp/static/partials/**/*'],
                tasks: ['copy:partials']
            },
            index: {
                files: ['src/main/webapp/static/*.html'],
                tasks: ['copy:index']
            },
            css: {
                files: ['src/main/webapp/static/css/*.css'],
                tasks: ['copy:css']
            }
        },

        copy: {
            js: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/js',
                    src: ['**/*.js'],
                    dest: 'target/wedoit/static/js'
                }]
            },
            fonts: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/fonts',
                    src: ['**/*'],
                    dest: 'target/wedoit/static/fonts'
                }]
            },
            partials: {
                files: [{
                    expand: true,
                    cwd: 'src/main/webapp/static/partials',
                    src: ['**/*'],
                    dest: 'target/wedoit/static/partials'
                }]
            },
            index: {
                files:[{
                    expand: true,
                    cwd: 'src/main/webapp/static',
                    src: ['*'],
                    dest: 'target/wedoit/static'
                }]
            },
            css: {
                files:[{
                    expand: true,
                    cwd: 'src/main/webapp/static/css',
                    src: ['*'],
                    dest: 'target/wedoit/static/css'
                }]
            }
        },

        uglify: {
            js: {
                files: [{
                    expand: true,
                    cwd: 'target/classes/js',
                    src: ['**/*.js','!**/*.min.js','!packages/**/*'],
                    dest: 'target/classes/js',
                    ext: '.min.js',
                    extDot: 'last'
                }]
            }
        },

        pkggen: {
            css: {
                webapp: 'target/classes',
                src: 'target/classes/css',
                ext: '.css',
                extMin: '.min.css',
                dest: 'target/classes/css/packages/css.json',
                concat: 'target/classes/css/packages'
            },
            js: {
                webapp: 'target/classes',
                src: 'target/classes/js',
                ext: '.js',
                extMin: '.min.js',
                dest: 'target/classes/js/packages/js.json',
                concat: 'target/classes/js/packages'
            }
        },

        jscs: {
            src: [
                'Gruntfile.js', 'src/main/js/**/*.js',
                '!src/main/js/_externals/*.js',
                '!src/main/js/_externals/**/*.js'],
            options: {
                config: 'src/build/jscs.json',
                verbose: true
            }
        },

        jshint: {
            src: [
                'Gruntfile.js',
                'src/main/js/**/*.js'
            ],
            jshintrc: 'src/build/jshint.json',
            options: {
                reporter: require('jshint-stylish')
            }
        }

    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-jscs');
    grunt.loadNpmTasks('grunt-newer');

    grunt.registerTask('default', [
        'newer:sass',
        'newer:copy',
        // 'validate:js'
    ]);

    grunt.registerTask('validate:js', 'javaScript validation', ['jshint', 'jscs']);

};