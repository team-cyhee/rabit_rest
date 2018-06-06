basePackage = input('basePackage: ')#'com.cyhee.rabit'
target = input('table: ')#'Goal'
targetLower = target.lower()

model = """
package {0}.{1}.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table
public class {2} {{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
}}
"""

dao = """
package {0}.{1}.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import {0}.{1}.model.{2};

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface {2}Repository extends PagingAndSortingRepository<{2}, Long> {{
}}
"""

serviceInterface = """
package {0}.{1}.service;

import {0}.{1}.model.{2};

public interface {2}Service {{
	Iterable<{2}> getAll{2}s();
	
	void add{2}({2} user);
	
	{2} get{2}(long id);
	
	void update{2}(long id, {2} {1}Form);
	
	void delete{2}(long id);
}}
"""

serviceBasic = """
package {0}.{1}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import {0}.{1}.dao.{2}Repository;
import {0}.{1}.model.{2};

@Service("basic{2}Service")
public class Basic{2}Service implements {2}Service {{
	@Autowired
	private {2}Repository {1}Repository;

	public Iterable<{2}> getAll{2}s() {{
		return {1}Repository.findAll();
	}}

	public void add{2}({2} {1}) {{
		{1}Repository.save({1});
	}}

	public {2} get{2}(long id) {{
		return {1}Repository.findById(id).get();
	}}

	public void update{2}(long id, {2} {1}Form) {{
		{2} {1} = {1}Repository.findById(id).get();
		{1} = {1}Form;
		{1}Repository.save({1});
	}}

	public void delete{2}(long id) {{
		{1}Repository.deleteById(id);
	}}
	
}}
"""

controller = """
package {0}.{1}.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import {0}.{1}.model.{2};
import {0}.{1}.service.{2}Service;
import {0}.web.model.ApiResponseEntity;

@RestController
@RequestMapping("rest/v1/{1}s")
public class {2}Controller {{
	@Resource(name="basic{2}Service")
	private {2}Service {1}Service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ApiResponseEntity<Iterable<{2}>> getAll{2}s() {{
        return new ApiResponseEntity<Iterable<{2}>>({1}Service.getAll{2}s(), HttpStatus.OK);
    }}
	
	@RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<Void> add{2}(@RequestBody {2} {1}) {{
    	{1}Service.add{2}({1});
        return new ApiResponseEntity<>(HttpStatus.CREATED);
    }}
    
    @RequestMapping(value="/{{id}}", method=RequestMethod.GET)
    public ApiResponseEntity<{2}> get{2}(@PathVariable long id) {{
    	return new ApiResponseEntity<>({1}Service.get{2}(id), HttpStatus.OK);
    }}
    
    @RequestMapping(value="/{{id}}", method=RequestMethod.PUT)
    public ApiResponseEntity<Void> update{2}(@PathVariable long id, @RequestBody {2} {1}Form) {{
    	{1}Service.update{2}(id, {1}Form);
        return new ApiResponseEntity<>(HttpStatus.OK); 
    }}
    
    @RequestMapping(value="/{{id}}", method=RequestMethod.DELETE)
    public ApiResponseEntity<Void> delete{2}(@PathVariable long id) {{
    	{1}Service.delete{2}(id);
        return new ApiResponseEntity<Void>(HttpStatus.ACCEPTED);
    }}
}}
"""

import os
dirList = ['dao', 'model', 'service', 'web']
for dirPath in dirList:
    if not os.path.exists(targetLower+'/'+dirPath):
        os.makedirs(targetLower+'/'+dirPath)

f = open(targetLower+'/model/'+target+'.java','w')
f.write(model.format(basePackage, targetLower, target))
f.close

f = open(targetLower+'/dao/'+target+'Repository.java','w')
f.write(dao.format(basePackage, targetLower, target))
f.close

f = open(targetLower+'/service/'+target+'Service.java','w')
f.write(serviceInterface.format(basePackage, targetLower, target))
f.close

f = open(targetLower+'/service/Basic'+target+'Service.java','w')
f.write(serviceBasic.format(basePackage, targetLower, target))
f.close

f = open(targetLower+'/web/'+target+'Controller.java','w')
f.write(controller.format(basePackage, targetLower, target))
f.close