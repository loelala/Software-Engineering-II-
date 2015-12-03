module.exports = function(grunt) {

    require('./grunt-pkggen')(grunt);

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
                tasks: ['copy:js', 'pkggen:js']
            },
            fonts: {
                files: ['src/main/webapp/static/fronts/**/*'],
                tasks: ['copy:fonts']
            },
            partials: {
                files: ['src/main/webapp/static/partials/**/*'],
                tasks: ['copy:webapp']
            },
            index: {
                files: ['src/main/webapp/static/index.html'],
                tasks: ['copy:index']
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
                    src: ['index.html'],
                    dest: 'target/wedoit/static'
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
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-scss-lint');
    grunt.loadNpmTasks('grunt-jscs');
    grunt.loadNpmTasks('grunt-newer');
    grunt.loadNpmTasks('grunt-sass');

    grunt.registerTask('default', [
        'newer:sass',
        'newer:copy',
        'pkggen:css', 'pkggen:js'
        // 'validate:js'
    ]);

    grunt.registerTask('minify', [
        'newer:sass',
        'newer:copy',
        'newer:cssmin:css',
        'newer:uglify:js',
        'pkggen:css',
        'pkggen:js',
        'concat:pkggen_css',
        'concat:pkggen_js'
    ]);

    grunt.registerTask('validate:js', 'javaScript validation', ['jshint', 'jscs']);

};