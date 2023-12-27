package br.com.rest.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.rest.mapper.DozerMapper;
import br.com.rest.unittests.mapper.mocks.MockPerson;
import br.com.rest.data.vo.v1.PessoaVO;
import br.com.rest.model.Pessoa;

public class DozerConverterTest {
    
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PessoaVO output = DozerMapper.parseObject(inputObject.mockEntity(), PessoaVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test0", output.getNome());
        assertEquals("Last Name Test0", output.getSobrenome());
        assertEquals("Addres Test0", output.getEndereco());
        assertEquals("Male", output.getSexo());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PessoaVO> outputList = DozerMapper.parseListObject(inputObject.mockEntityList(), PessoaVO.class);
        PessoaVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getNome());
        assertEquals("Last Name Test0", outputZero.getSobrenome());
        assertEquals("Addres Test0", outputZero.getEndereco());
        assertEquals("Male", outputZero.getSexo());
        
        PessoaVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getNome());
        assertEquals("Last Name Test7", outputSeven.getSobrenome());
        assertEquals("Addres Test7", outputSeven.getEndereco());
        assertEquals("Female", outputSeven.getSexo());
        
        PessoaVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test12", outputTwelve.getNome());
        assertEquals("Last Name Test12", outputTwelve.getSobrenome());
        assertEquals("Addres Test12", outputTwelve.getEndereco());
        assertEquals("Male", outputTwelve.getSexo());
    }

    @Test
    public void parseVOToEntityTest() {
    	Pessoa output = DozerMapper.parseObject(inputObject.mockVO(), Pessoa.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getNome());
        assertEquals("Last Name Test0", output.getSobrenome());
        assertEquals("Addres Test0", output.getEndereco());
        assertEquals("Male", output.getSexo());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Pessoa> outputList = DozerMapper.parseListObject(inputObject.mockVOList(), Pessoa.class);
        Pessoa outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getNome());
        assertEquals("Last Name Test0", outputZero.getSobrenome());
        assertEquals("Addres Test0", outputZero.getEndereco());
        assertEquals("Male", outputZero.getSexo());
        
        Pessoa outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getNome());
        assertEquals("Last Name Test7", outputSeven.getSobrenome());
        assertEquals("Addres Test7", outputSeven.getEndereco());
        assertEquals("Female", outputSeven.getSexo());
        
        Pessoa outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getNome());
        assertEquals("Last Name Test12", outputTwelve.getSobrenome());
        assertEquals("Addres Test12", outputTwelve.getEndereco());
        assertEquals("Male", outputTwelve.getSexo());
    }
}
