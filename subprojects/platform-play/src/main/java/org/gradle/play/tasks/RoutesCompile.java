/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.play.tasks;

import org.gradle.api.Incubating;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.compile.BaseForkOptions;
import org.gradle.language.base.internal.compile.Compiler;
import org.gradle.platform.base.internal.toolchain.ToolProvider;
import org.gradle.play.internal.CleaningPlayToolCompiler;
import org.gradle.play.internal.routes.DefaultRoutesCompileSpec;
import org.gradle.play.internal.routes.RoutesCompileSpec;
import org.gradle.play.internal.toolchain.PlayToolChainInternal;
import org.gradle.play.platform.PlayPlatform;
import org.gradle.play.toolchain.PlayToolChain;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Task for compiling routes templates into Scala code.
 */
@Incubating
public class RoutesCompile extends SourceTask {

    /**
     * Target directory for the compiled route files.
     */
    private File outputDirectory;

    /**
     * Additional imports used for by generated files.
     */
    private List<String> additionalImports = new ArrayList<String>();

    private boolean javaProject;
    private boolean namespaceReverseRouter;
    private boolean generateReverseRoutes = true;
    private PlayPlatform platform;
    private BaseForkOptions forkOptions;
    private boolean staticRoutesGenerator = true;

    /**
     * Returns the directory to generate the parser source files into.
     *
     * @return The output directory.
     */
    @OutputDirectory
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Specifies the directory to generate the parser source files into.
     *
     * @param outputDirectory The output directory. Must not be null.
     */
    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Specifies the additional imports of the Play Routes compiler.
     */
    public List<String> getAdditionalImports() {
        return additionalImports;
    }

    /**
     * Returns the additional imports of the Play Routes compiler.
     *
     * @return The additional imports.
     */
    public void setAdditionalImports(List<String> additionalImports) {
        this.additionalImports.addAll(additionalImports);
    }


    @TaskAction
    void compile() {
        RoutesCompileSpec spec = new DefaultRoutesCompileSpec(getSource().getFiles(), getOutputDirectory(), getForkOptions(), isJavaProject(), isNamespaceReverseRouter(), isGenerateReverseRoutes(), staticRoutesGenerator);
        new CleaningPlayToolCompiler<RoutesCompileSpec>(getCompiler(), getOutputs()).execute(spec);
    }

    private Compiler<RoutesCompileSpec> getCompiler() {
        ToolProvider select = ((PlayToolChainInternal) getToolChain()).select(platform);
        return select.newCompiler(RoutesCompileSpec.class);
    }

    public boolean isJavaProject() {
        return javaProject;
    }

    public void setPlatform(PlayPlatform platform) {
        this.platform = platform;
    }

    /**
     * Returns the tool chain that will be used to compile the routes source.
     *
     * @return The tool chain.
     */
    @Incubating
    @Inject
    public PlayToolChain getToolChain() {
        throw new UnsupportedOperationException();
    }

    /**
     * The fork options to be applied to the Routes compiler.
     *
     * @return The fork options for the Routes compiler.
     */
    public BaseForkOptions getForkOptions() {
        if (forkOptions == null) {
            forkOptions = new BaseForkOptions();
        }
        return forkOptions;
    }

    /**
     * Whether the reverse router should be namespaced.
     */
    public boolean isNamespaceReverseRouter() {
        return namespaceReverseRouter;
    }

    /**
     * Sets whether or not the reverse router should be namespaced.
     * @param namespaceReverseRouter
     */
    public void setNamespaceReverseRouter(boolean namespaceReverseRouter) {
        this.namespaceReverseRouter = namespaceReverseRouter;
    }

    /**
     * Whether a reverse router should be generated.
     */
    public boolean isGenerateReverseRoutes() {
        return generateReverseRoutes;
    }

    /**
     * Sets whether or not a reverse router should be generated.
     * @param generateReverseRoutes
     */
    public void setGenerateReverseRoutes(boolean generateReverseRoutes) {
        this.generateReverseRoutes = generateReverseRoutes;
    }

    /**
     * Is the static routes generator (<code>play.routes.compiler.StaticRoutesGenerator</code>) used for
     * generating routes?  Default is true.
     *
     * @return true if StaticRoutesGenerator will be used to generate routes,
     * false if InjectedRoutesGenerator will be used to generate routes.
     */
    public boolean getStaticRoutesGenerator() {
        return staticRoutesGenerator;
    }

    /**
     * Configure if the static routes generator should be used to generate routes.
     *
     * @param staticRoutesGenerator true - use StaticRoutesGenerator
     * false - use InjectedRoutesGenerator
     */
    public void setStaticRoutesGenerator(boolean staticRoutesGenerator) {
        this.staticRoutesGenerator = staticRoutesGenerator;
    }
}
