/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package roman.flink.mybatis.flinkmybatis;

import com.google.common.base.Objects;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionManager;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A generic MyBatis mapper provider.
 */
public final class MapperProvider<T> implements Provider<T> {

  private final Class<T> mapperType;

  @Inject
  private SqlSessionManager sqlSessionManager;

  @Inject
  private MapperHelper mapperHelper;

  private final Object processConfigurationLock = new Object();

  public MapperProvider(Class<T> mapperType) {
    this.mapperType = mapperType;
  }

  public T get() {
    Configuration configuration = sqlSessionManager.getConfiguration();
    //直接针对接口处理通用接口方法对应的 MappedStatement 是安全的，通用方法不会出现 IncompleteElementException 的情况
    if (configuration.hasMapper(this.mapperType) && mapperHelper != null && mapperHelper.isExtendCommonMapper(this.mapperType)) {
      synchronized (processConfigurationLock){
        mapperHelper.processConfiguration(configuration, this.mapperType);
      }
    }
    return this.sqlSessionManager.getMapper(mapperType);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.mapperType);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    MapperProvider other = (MapperProvider) obj;
    return Objects.equal(this.mapperType, other.mapperType);
  }
}
